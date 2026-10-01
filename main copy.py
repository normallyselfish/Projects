from tkinter import *
import pywinstyles
import numpy
import matplotlib.pyplot as plot   

field = [[' '] * 80] * 25
a=0
offset=5
x=offset
y=offset
size=19
pointerPos = [0,0]
pointerDir = 'R'
# field[5][0] = '>'
root = Tk()
root.geometry('1920x1200')

canvas = Canvas(root,width=1920,height=1200)
canvas.pack()

# Create grid
canvas.create_line(offset,y,size*len(field[0])+offset,y)
canvas.create_line(x,offset,x,size*len(field)+offset)

for i in range(len(field)):
    y+=size
    canvas.create_line(offset,y,size*len(field[0])+offset,y)
for i in range(len(field[0])):
    x+=size
    canvas.create_line(x,offset,x,size*len(field)+offset)
#Spawn pointer
pointer=canvas.create_rectangle(offset,offset,25,25,fill='#550055',width=0)
def move(x,y):
    canvas.move(pointer,x*size,y*size)

# Movement handlers
def moveLeft():
    if(pointerPos[0]>0):
        pointerPos[0] -= 1
        move(-1,0)
        print(pointerPos)
    else:
        pointerPos[0] = len(field)-1
        move(len(field[0])-1,0)
        print(pointerPos)
    cmd(pointerPos[0],pointerPos[1])

def moveRight():
    if(pointerPos[0]<len(field)-1):
        pointerPos[0] += 1
        move(1,0)
        print(pointerPos)
    else:
        pointerPos[0]=0
        move(-len(field[0])+1,0)
        print(pointerPos)
    cmd(pointerPos[0],pointerPos[1])

def moveUp():
    if(pointerPos[1]>0):
        pointerPos[1] -= 1
        move(0,-1)
        print(pointerPos)
    else:
        pointerPos[1]=len(field[0])-1
        move(0,len(field[0])-1)
        print(pointerPos)
    cmd(pointerPos[0],pointerPos[1])

def moveDown():
    if(pointerPos[1]<len(field[0])-1):
        pointerPos[1] += 1
        move(0,1)
        print(pointerPos)
    else:
        pointerPos[1]=0
        move(0,-len(field[0])+1)
        print(pointerPos)
    cmd(pointerPos[0],pointerPos[1])

# Command List

def cmd(x,y):
    inputPos = field[x][y]
    global pointerDir
    print(inputPos)
    if(inputPos == '@'):
        root.destroy()
    if(inputPos == '>'):
        pointerDir = 'R'
    if(inputPos == '<'):
        pointerDir = 'L'
    if(inputPos == 'v'):
        pointerDir = 'D'
    if(inputPos == '^'):
        pointerDir = 'U'
    if(inputPos == ' '):
        pass

# Debug movments
root.bind('a',lambda x: moveLeft())
root.bind('d',lambda x: moveRight())
root.bind('w',lambda x: moveUp())
root.bind('s', lambda x: moveDown())

def run():
    cmd(pointerPos[0],pointerPos[1])
    if(pointerDir=="R"):
        moveRight()
    if(pointerDir=="L"):
        moveLeft()
    if(pointerDir=="U"):
        moveUp() 
    if(pointerDir=="D"):
        moveDown()
    root.after(500,run)
# run()
root.mainloop()