//服务器UserService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.TreeMap;

public interface UserService extends Remote{
	//存储用户名及对应的密码
	public Map<String,String> users_InfoMap = new TreeMap<String,String>();

	public boolean login(String username, String password) throws RemoteException;

	public boolean logout() throws RemoteException;

	public boolean register(String username,String password) throws RemoteException;

	public boolean deleteUser(String username) throws RemoteException;

	public boolean modifyPassword(String username,String newPassword) throws RemoteException;
}
