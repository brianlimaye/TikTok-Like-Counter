public class Comment {

	private long cid;
	private String message; 

	public Comment() {
		this.cid = 0l;
		this.message = "";
	}

	public Comment(long cid, String message) {
		this.cid = cid;
		this.message = message;
	}

	public long getCID() {
		return cid;
	}

	public String getMessage() {
		return message;
	}
}