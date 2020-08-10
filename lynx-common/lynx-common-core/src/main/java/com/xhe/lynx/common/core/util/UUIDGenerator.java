package com.xhe.lynx.common.core.util;

/**
 * @classname UUIDGenerator
 * @description 雪花算法uuid工具类
 * @date 2020-08-10 10:16:36
 * @author xhe
 */
public class UUIDGenerator {

    /**
     * Generate uuid long.
     * @return the long
     */
    public static long generateUUID() {
        return IdWorker.getInstance().nextId();
    }

    /**
     * Init.
     *
     * @param serverNode the server node id
     */
    public static void init(Long serverNode) {
        IdWorker.init(serverNode);
    }
}
