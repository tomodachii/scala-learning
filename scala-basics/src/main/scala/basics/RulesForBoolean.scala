package basics

def loop: Boolean = loop

// this and call by value
def and(x: Boolean, y: Boolean): Boolean = if x then y else false

def andCBN(x: Boolean, y: => Boolean): Boolean = if x then y else false