# pure_hibernate
Use pure hibernate with HibernateSessionFactory management

For non-SpringFramework environment,  
use HibernateSessionFactory to manage session to avoid getSession(), session.close() operation per each.  

在沒有Spring framework的環境下  
使用 HibernateSessionFactory 來管理session  
避免每次操作db時都要 getSession(), session.close()的流程。  
