package health.hms;

import org.testng.annotations.Test;

import health.hms.tests.OTP;

public class HMS extends Base{

	@Test
	public void Initial() 
	{
				
		OTP a = l.login("doc@gmail.com", "Gyrit@123");
		a.OTPpage();
		
	}

}
