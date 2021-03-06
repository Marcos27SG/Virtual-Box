package virtualBox;

import org.virtualbox_6_1.IMachine;
import org.virtualbox_6_1.IVirtualBox;
import org.virtualbox_6_1.VBoxException;
import org.virtualbox_6_1.VirtualBoxManager;


/**
 *
 * @author MARCOS
 */
public class test {
    public static void main(String[] args) {
		VirtualBoxManager virtualBoxManager = VirtualBoxManager.createInstance(null);
		virtualMachine awesomeMachine = new virtualMachine();
		

		boolean ws = false;
		String url = "http://localhost:18083";
		String user = null;
		String passwd = null;

		try {
			virtualBoxManager.connect(url, user, passwd);
		} catch (VBoxException e) {
			e.printStackTrace();
			System.out.println("Cannot connect, start webserver first!");
		}

		try {
			IVirtualBox iVirtualBox = virtualBoxManager.getVBox();

			if (iVirtualBox != null) {
				System.out.println("VirtualBox version: " + iVirtualBox.getVersion() + "\n");
				

				
				System.out.println("Please Wait... Creating a new machine...");				
				awesomeMachine.createVMbeta(virtualBoxManager, iVirtualBox);
				System.out.println("Please Wait... Turning on the new machine \n WARNING!!! WARNING!!! The new machine will turn off in 20 seconds");				
				awesomeMachine.turnOn_VM(virtualBoxManager, iVirtualBox);
				
                toWait(20000);                
                awesomeMachine.turnOff_VM(virtualBoxManager, iVirtualBox);

			}
		} catch (VBoxException e) {
			awesomeMachine.printErrorInfo(e);
			System.out.println("Java stack trace:");
			e.printStackTrace();
		} catch (RuntimeException e) {
			System.out.println("Runtime error: " + e.getMessage());
			e.printStackTrace();
		}


		virtualBoxManager.waitForEvents(0);
		if (ws) {
			try {
				virtualBoxManager.disconnect();
			} catch (VBoxException e) {
				e.printStackTrace();
			}
		}

		virtualBoxManager.cleanup();		

	}

	static void toWait(long waitMillis) {
		System.out.println("Starting Waiting "+System.currentTimeMillis());
		long end = System.currentTimeMillis() + waitMillis;
		boolean isItTime = false;
		while (!isItTime) {
			if (System.currentTimeMillis() >= end)
				isItTime = true;

		}
		System.out.println("Finishing Waiting");
	}
}
