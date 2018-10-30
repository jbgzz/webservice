package cn.elane.logs;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logs {

    private static Logger logger = LogManager.getLogger(Logs.class);

    public static void Debuglog(String msg,String entity,Class obj){
        logger.debug(msg+":"+entity);
    }

    public static void Warnlog(String msg,String entity){
        logger.warn(msg+":"+entity);
    }

    public static void Warnlog(String msg,Throwable e){
        logger.warn(msg,e);
    }

    public static void Errorlog(String msg,Throwable e){
        logger.error(msg,e);
    }

    public static void Infolog(String msg,String entity){
        logger.info(msg+":"+entity);
    }
}
