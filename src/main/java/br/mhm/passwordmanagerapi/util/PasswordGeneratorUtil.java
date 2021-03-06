package br.mhm.passwordmanagerapi.util;

public class PasswordGeneratorUtil {

	public static String generatePassword(Integer size) {
		String randomPassword = "";

		for (int i = 0; i < size; i++) {
			randomPassword += randomCharacter();
		}

		return randomPassword;
	}

	public static char randomCharacter() {
		int rand = (int) (Math.random() * 62);
		
		if (rand <= 9) {
			int number = rand + 48;
			return (char) (number);
		} 
		else if (rand <= 35) {
			int uppercase = rand + 55;
			return (char) (uppercase);
		}
		else {
			int lowercase = rand + 61;
			return (char) (lowercase);
		}
	}
}
