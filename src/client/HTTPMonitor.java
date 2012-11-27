package client;

public class HTTPMonitor {
	private byte[] image;
	private boolean bool = false;

	public synchronized void storeImage(byte[] image) {
		System.out.println("StoreImage anropad med " + bool);
		if(bool){
			this.image = image;
			bool = false;
			notifyAll();
			System.out.println("notifyAll() i storeImage");
		}
		
	}
	
	public synchronized boolean checkState(){
		return bool;
	}
	
	public synchronized byte[] requestImage() {
		System.out.println("requestImage() anropad");
		bool = true;
		while(bool){
			try {
				System.out.println("Går in i wait()");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Returnerar bild i requestImage()");
		return image;
	}

}
