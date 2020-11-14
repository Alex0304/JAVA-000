package com.weekwork.gc;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class OomLeakDemo {

    public List<User> distinc() throws InterruptedException {
        ArrayList<User> users = CollUtil.newArrayList();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(()->{
            try {
                for (int i = 0;i<100;i++){
                    for (int j=0;j<10000;j++){
                        User user = new User(String.valueOf(j));
                        if(!users.contains(user)){
                            users.add(user);
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }

        }).start();
        countDownLatch.await();
        return users;
    }

    class User{

        private String id;

        public User(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public User setId(String id) {
            this.id = id;
            return this;
        }
    }
}
