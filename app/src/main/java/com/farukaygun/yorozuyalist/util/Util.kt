package com.farukaygun.yorozuyalist.util

class Util {
	companion object {
		fun generateCodeChallenge(): String {
			val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + '-' + '.' + '_' + '~'

			return (1..128)
				.map { allowedChars.random() } // return random element from collection
				.joinToString("") // add element to new string
		}
	}
}