from tkinter import *
import MATT_AI_BOT 
import googletrans
from googletrans import Translator

translator = Translator()
MATT_AI_bot=MATT_AI_BOT.MATT_AI_ChatBot()

# GUI
root = Tk()
root.title("MATT_AI_Chatbot")

BG_GRAY = "#ABB2B9"
BG_COLOR = "#17202A"
TEXT_COLOR = "#EAECEE"

FONT = "Helvetica 14"
FONT_BOLD = "Helvetica 13 bold"

root.configure(bg=BG_COLOR)
root.geometry('775x670')
root.resizable(0,0)

# Send function
def send():
	send = "     You     ->  " + e.get()
	txt.insert(END, "\n" + send)

	user = e.get().lower()
	txt.insert(END, "\n" + "  Matt_AI  ->  "+ MATT_AI_BOT.risposta(e.get(), MATT_AI_bot,translator))
    
	e.delete(0, END)

def Close():
    root.destroy()

lable1 = Label(root, bg=BG_COLOR, fg=TEXT_COLOR, text="Benvenuto chatta con MATT_AI_BOT !", font=FONT_BOLD, pady=15, width=50, height=1).grid(row=0)

txt = Text(root, bg=BG_COLOR, fg=TEXT_COLOR, font=FONT, width=70)
txt.grid(row=1, column=0, columnspan=2)

scrollbar = Scrollbar(txt)
scrollbar.place(relheight=1, relx=0.974)

e = Entry(root, bg="#2C3E50", fg=TEXT_COLOR, font=FONT, width=65)
e.grid(row=2, column=0)

txt.insert(END, "\n" + "  Matt_AI  ->  Ciao, sono un'intelligenza artificiale, sviluppata da mio padre Mattia Gatto!")
	
send = Button(root, text="Send", font=FONT_BOLD, bg=BG_GRAY,command=send).grid(row=2, column=1)

exit = Button(root, text="Exit", font=FONT_BOLD, bg=BG_GRAY,command=Close).grid(row=3, column=0, pady = 10)


root.mainloop()
