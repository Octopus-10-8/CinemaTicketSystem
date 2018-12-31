package test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import utils.Utils;

/**  日志测试
 * Date: 2018/12/25 0025
 **/
public class LogTest {

    static Logger logger = Logger.getLogger(Utils.class);
    public static void main(String[] args) throws InterruptedException {
        PropertyConfigurator.configure("F:\\Intellij-idea\\Iotek\\cinemaTicketSystem\\src\\properties\\log4j.properties");

            logger.trace("跟踪信息");
            logger.debug("调试信息");
            logger.info("输出信息");
            logger.warn("警告信息");
            logger.error("错误信息");
            logger.fatal("致命信息");

    }
}
