package daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class BaseDao {
	private File file;
	private FileInputStream fis;
	private FileOutputStream fos;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public BaseDao() {
		super();
	}

	public BaseDao(File file) {
		super();
		this.file = file;
	}

	/**
	 * IO数据对象的写入
	 * @param al
	 * @param <T>
	 */
	public <T> void write(ArrayList<T> al) {
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(al);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * IO对象的读出
	 * @param <T>
	 * @return
	 */
	public <T> ArrayList<T> read() {
		ArrayList<T> al = null;
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			al = (ArrayList<T>) ois.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			al = new ArrayList<>();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return al;
	}

	/**
	 * IO资源的关闭
	 */
	public void closeAll() {

		if (ois != null) {
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
