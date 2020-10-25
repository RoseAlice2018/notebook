public class LazySingleton{
    private LazySingleton(){}
    private static LazySingleton LazySingleton = NULL ;
    public static LazySingleton getSingleton(){
        if(LazySingleton == NULL){
            if(LazySingleton == NULL){
                LazySingleton = new LazySingleton();
            }
            return LazySingleton;
        }
    }
}