package com.farukaygun.yorozuyalist.util

class Util {
	companion object {
		private var codeVerifier = ""
		fun generateCodeChallenge(): String {
			if (codeVerifier.isEmpty()) {
				val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '-' + '.' + '_' + '~'

				codeVerifier = (1..64)
					.map { allowedChars.random() }
					.joinToString("")
			}
			return codeVerifier
		}
	}
}