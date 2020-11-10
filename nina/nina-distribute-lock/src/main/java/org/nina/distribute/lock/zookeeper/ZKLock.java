package org.nina.distribute.lock.zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AutoCloseable:实现锁的自动关闭 Watcher：实现zookeeper的观察器
 * 
 * @author riverplant
 *
 */
public class ZKLock implements AutoCloseable, Watcher {

	private ZooKeeper zooKeeper;
	private final String CONNECTIONSTRING = "localhost:2181";
	private final int sessionTimeout = 10000;
	private String znode;
	private static final Logger log = LoggerFactory.getLogger(ZKLock.class);

	public ZKLock() throws IOException {
		/**
		 * connectString: sessionTimeout: watcher:Zookeeper的观察器,ZKLock已经实现了Watcher
		 * 所以传入自己就行
		 */
		this.zooKeeper = new ZooKeeper(CONNECTIONSTRING, sessionTimeout, this);
	}

	/**
	 * 获取锁的方法
	 * 
	 * @param businessCode：业务代码
	 * @return
	 */
	public boolean getLock(String businessCode) {
		// 1.创建业务的根节点
		try {
			Stat stat = zooKeeper.exists("/" + businessCode, false);
			// 2.判断节点是否存在，如果节点不存在，创建节点
			if (stat == null) {
				/**
				 * acl:权限 createMode：瞬时节点还是持久节点 创建节点和锁没有关系，主要是决定哪个业务放在哪个节点下 所以可以创建持久节点
				 */
				zooKeeper.create("/" + businessCode, businessCode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
			// 3.创建瞬时有序节点，在多线程中，根据排序来获得锁
			/**
			 * 瞬时节点的位置：/order/order_000001 序号是自动生成
			 */
			znode = zooKeeper.create("/" + businessCode + "/" + businessCode + "_", businessCode.getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			// 获得所有的子节点
			List<String> chirenNodes = zooKeeper.getChildren("/" + businessCode, false);
			// 对所有的子节点进行排序
			Collections.sort(chirenNodes);
			// 获得最小的子节点
			String firstNode = chirenNodes.get(0);
			// 4.判断znode是否是最小，是否可以获得锁
			if (StringUtils.endsWith(znode, firstNode)) {
				return true;
			}
			/**
			 * 不是第一个子节点，则监听前一个节点 将第一个节点置为上一个节点
			 */
			String lastNode = firstNode;
			for (String node : chirenNodes) {
				// 当该节点是第二个节点，只需要监听第一个节点
				if (StringUtils.endsWith(znode, node)) {
					zooKeeper.exists("/" + businessCode + "/" + lastNode, true);
					break;
				} else {
					// 当该节点不是第二个节点，需要监听前一个节点
					lastNode = node;
				}
			}
			/**
			 * 固定写法,在监听器监听的节点未释放之前，当前线程等待
			 */
			synchronized (this) {
				wait();
			}

			return true;

		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override // 监听器监听的节点如果消失，会进入该方法
	public void process(WatchedEvent event) {
		// 如果监听器监听的节点如果消失
		if (event.getType() == Event.EventType.NodeDeleted) {
			synchronized (this) {
				// 唤醒线程
				notify();
			}
		}

	}

	@Override
	public void close() throws Exception {
		zooKeeper.delete(znode, -1);
		zooKeeper.close();
		log.info("锁已经释放");
	}

}
