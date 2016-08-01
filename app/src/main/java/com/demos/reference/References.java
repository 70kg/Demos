package com.demos.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by Mr_Wrong on 16/3/21.
 * 测试引用的一些东西 类似软引用 弱引用
 */
public class References {


    private static ReferenceQueue<veryBig> rq = new ReferenceQueue<>();
    private static WeakReference<veryBig> reference;
    public static void main(String[] args) {

        veryBig veryBig = rq.poll().get();

        reference = new WeakReference<veryBig>(veryBig);
        reference.get();
    }

    class veryBig {
        private final static int SIZE = 10000;
        private long[] la = new long[SIZE];
        private String ident;

        public veryBig(String id) {
            this.ident = id;
        }

        @Override
        public String toString() {
            return ident;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("析构函数");
        }

        private void test(){

        }
    }
}