package com.farukaygun.yorozuyalist.util

class Util {
	companion object {
		private var codeVerifier = ""
		fun generateCodeChallenge(): String {
			if (codeVerifier.isEmpty()) {
				val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '-' + '.' + '_' + '~'

				codeVerifier = (1..64)
					.map { allowedChars.random() } // return random element from collection
					.joinToString("") // add element to new string
			}
			return codeVerifier
		}
	}
}