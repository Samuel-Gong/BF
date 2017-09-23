//服务器IOService的Stub，内容相同
package service;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
public interface IOService extends Remote{

	public boolean writeFile(String file,String fileName)throws RemoteException;

	public String readFile(String userId, String fileName)throws RemoteException;

	//用于open操作，用户只能打开自己存储的文件
	public String[] readFileList(String userId)throws RemoteException;

	//在用户信息发生修改时，同时修改存储在文件中的用户信息
	public boolean writeUsersInfo(File file,String username,String password) throws RemoteException;

	//在服务器启动时，将所有用户的信息放到一个map中
	public Map<String,String> readUsersInfo(File file) throws RemoteException;

	//创建用户目录
	public boolean makeDir(String username) throws RemoteException;

	//清空文件内容
	public void clearFile(File file) throws RemoteException;

	//接收code和param并返回结果
	public String execute(String code,String param) throws RemoteException;

	//检查文件名是否重名
	public boolean isNamed(String userId,String fileName) throws RemoteException;

	//新建一个文件
	public boolean newFile(String fileName) throws RemoteException;

	//删除文件夹
	public boolean deleteDir(String username) throws RemoteException;

	//直接根据路径读取file
	public String readFileThroughPath(String path) throws RemoteException;
}
