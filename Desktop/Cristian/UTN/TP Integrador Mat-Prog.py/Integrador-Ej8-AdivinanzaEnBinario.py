print ("Bienvenido al juego de adivinanzas de números binario y decimal! ")
#decimal = 0
binario = 0

decimal = int(input("¿A qué número decimal equivale el número binario 110110? "))
while decimal != 110:
    decimal = int(input("No adivinaste. Vuelve a intentarlo!"))
    
print ("Excelente. Has adivinado el número")

binario = int(input("¿Ahora te propongo adivinar a qué número equivale el 1302 en binario?"))
while binario != 10100010110:
    binario = int(input("Intentalo de nuevo!"))

print ("Excelente")

