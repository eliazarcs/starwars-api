package eliazarcs.com.starwars.api.dto;

public class ApiResponse<T extends Object> {

	private int status;
	private String message;
	private T result;

	public ApiResponse(int status, String message, T result){
	    this.status = status;
	    this.message = message;
	    this.result = result;
    }

	public ApiResponse(int status, T result){
	    this.status = status;
	    this.result = result;
    }
	
    public ApiResponse(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
	public String toString() {
		return "ApiResponse [statusCode=" + status + ", message=" + message +"]";
	}


}
