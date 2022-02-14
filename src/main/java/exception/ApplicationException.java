package exception;

public class ApplicationException extends RuntimeException{

    public ApplicationException(){
        super("System Exception");
    }

    public ApplicationException(String msg){
        super(msg);
    }
}
