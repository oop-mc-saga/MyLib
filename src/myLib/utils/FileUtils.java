package myLib.utils;

import java.io.*;

/**
 *
 * @author tadaki
 */
public class FileUtils {

    //このクラスのインスタンスは作らない
    private FileUtils() {
    }

    /**
     * File をreaderで開ける
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    static public BufferedReader openReader(File file) 
            throws FileNotFoundException {
        return new BufferedReader(
                new InputStreamReader(new FileInputStream(file)));
    }

    static public BufferedReader openReader(String filename)
            throws FileNotFoundException {
        return openReader(new File(filename));
    }

    /**
     * Readerから内容を読みだし、文字列として返す
     *
     * @param in
     * @return
     * @throws IOException
     */
    static public String readFromReader(BufferedReader in) throws IOException {
        String nl = System.getProperty("line.separator");//改行コード
        StringBuilder buf = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {//一行毎に読みだし
            buf.append(line);
            buf.append(nl);//改行
        }
        in.close();
        return buf.toString();
    }

    /**
     * File をwriterとして開く
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    static public BufferedWriter openWriter(File file) 
            throws FileNotFoundException {
        return new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file)));
    }

    static public BufferedWriter openWriter(String filename)
            throws FileNotFoundException {
        return openWriter(new File(filename));
    }

    /**
     * カンマ区切りで、対象オブジェクトを出力する
     *
     * @param out
     * @param objects
     * @throws IOException
     */
    public static void writeCSV(BufferedWriter out, Object... objects)
            throws IOException {
        writeVars(out, ',', objects);
    }

    /**
     * スペース区切りで、対象オブジェクトを出力する
     *
     * @param out
     * @param objects
     * @throws IOException
     */
    public static void writeSSV(BufferedWriter out, Object... objects)
            throws IOException {
        writeVars(out, ' ', objects);
    }

    /**
     * 任意の区切り文字で、対象オブジェクトを出力する
     *
     * @param out
     * @param sep　区切り文字
     * @param objects
     * @throws IOException
     */
    public static void writeVars(BufferedWriter out, char sep,
            Object... objects)
            throws IOException {
        String str = object2String(sep, objects);
        out.write(str);
        out.newLine();
    }

    /**
     * ファイル名中の拡張子を調べる
     *
     * @param filename
     * @return 拡張子の文字列
     */
    static public String getExtention(String filename) {
        String ext = null;
        int index = filename.lastIndexOf(".");
        if (index > 0) {
            ext = filename.substring(index + 1);
            if (ext.length() < 1) {
                ext = null;
            }
        }
        return ext;
    }

    /**
     * Objectを区切り文字で連結する
     *
     * @param sep　区切り文字
     * @param objects
     * @return
     */
    public static String object2String(char sep, Object... objects) {
        StringBuilder sb = new StringBuilder();
        int n = objects.length;
        for (int i = 0; i < n - 1; i++) {
            sb.append(objects[i]).append(sep);
        }
        sb.append(objects[n - 1]);
        return sb.toString();
    }

}
