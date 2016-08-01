package com.demos.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by Mr_Wrong on 16/2/9.
 */
public class BookManagerImpl extends Binder implements IBookManageer {



    static final String DESCRIPTOR = "com.demos.aidl";

    public BookManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    //判断是同一个进程 就直接返回服务端的binder 否则返回proxy的iBookmanager  是给客户端调用的
    public static IBookManageer asInterface(IBinder binder) {
        //这个binder对象是驱动给我们的
        if (binder == null) {
            return null;
        }
        IInterface iin = binder.queryLocalInterface(DESCRIPTOR);
        if (iin != null && iin instanceof IBookManageer) {
            return ((IBookManageer) iin);
        }
        return new BookManagerImpl.Proxy(binder);

    }

    //------下面的运行在远程
    @Override
    public List<Book> getBookList() throws RemoteException {
        //这个是在远程服务里面去实现具体的逻辑
        return null;
    }

    @Override
    public void addBook(Book book) throws RemoteException {
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case IBookManageer.TRANSACTION_getBookList: {
                data.enforceInterface(DESCRIPTOR);
                List<Book> result = this.getBookList();
                reply.writeTypedList(result);
                reply.writeNoException();
                return true;
            }
            case IBookManageer.TRANSACTION_addBook: {
                data.writeNoException();
                data.enforceInterface(DESCRIPTOR);
                Book book;
                if (data.readInt() != 0) {
                    book = Book.CREATOR.createFromParcel(data);
                } else {
                    book = null;
                }
                this.addBook(book);
                return true;
            }
        }

        return super.onTransact(code, data, reply, flags);
    }
//--------上面的在远程

    //在前面判断之后  如果是运行在远程的  返回实例给客户端 下面的是在客户端运行
    //这只是个代理  屁正事不干  就是把参数传递给远程的Server  然后去执行Server的onTransact方法
    public static class Proxy implements IBookManageer {
        private IBinder mRemote;


        public Proxy(IBinder binder) {
            this.mRemote = binder;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Parcel date = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            date.writeInterfaceToken(DESCRIPTOR);
            List<Book> result;
            try {
                //调用远程发服务transact方法  最终是去调用远程Server的onTransact方法
                mRemote.transact(IBookManageer.TRANSACTION_getBookList, date, reply, 0);
                reply.writeNoException();
                result = reply.createTypedArrayList(Book.CREATOR);
            } finally {
                reply.recycle();
                date.recycle();
            }
            return result;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Parcel date = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            try {
                date.writeInterfaceToken(DESCRIPTOR);
                if (book != null) {
                    date.writeInt(1);
                    book.writeToParcel(date, 0);
                } else {
                    date.writeInt(0);
                }
                mRemote.transact(IBookManageer.TRANSACTION_addBook, date, reply, 0);
                reply.writeNoException();
            } finally {
                date.recycle();
                reply.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
