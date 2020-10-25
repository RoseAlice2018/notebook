public class LazySingleton{
    private LazySingleton(){}
    private static volatile LazySingleton LazySingleton = NULL ;
    public static LazySingleton getSingleton(){
        if(LazySingleton == NULL){
            // 第一重判断
            if(LazySingleton == NULL){
                //锁定代码块
                synchronized(LazySingleton.class){
                    //第二重判断
                        if(LazySingleton == NULL)
                        {
                            LazySingleton = new LazySingleton();
                        }
                }
                
            }
            return LazySingleton;
        }
    }
}