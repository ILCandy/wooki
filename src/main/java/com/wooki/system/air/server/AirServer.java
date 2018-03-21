package com.wooki.system.air.server;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author wwy
 * @version 1.0
 * @description com.wooki.air
 * @date 2018/3/19
 */
@Component
public class AirServer implements ApplicationRunner{

    private static int port = 8899;
    private static Thread clientListener;

//    public static void main(String[] args) {
//		//开启线程监听客户端
//		clientListener = new Thread(new ClientListener(port));
//		clientListener.start();
//	}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        clientListener = new Thread(new ClientListener(port));
        clientListener.start();
    }
}
