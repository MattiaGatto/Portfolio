# -*- coding: utf-8 -*-
"""# LIBRERIE NECESSARIE ALL'UTILIZZO
"""

#!pip install chatterbot==1.0.4
#!pip install chatterbot_corpus
#!pip install googletrans==3.1.0a0

# Importing Relevant Libraries
import json
import string
import random
import time

time.clock = time.time

import chatterbot
from chatterbot import ChatBot
from chatterbot.trainers import ListTrainer

import googletrans
from googletrans import Translator
from chatterbot.trainers import ChatterBotCorpusTrainer

"""
# MATT_AI_BOT
"""

def MATT_AI_ChatBot():
  
  my_bot = ChatBot(name='MATT_AI', read_only=True, logic_adapters=[
                 'chatterbot.logic.MathematicalEvaluation', 'chatterbot.logic.BestMatch'])
  corpus_trainer = ChatterBotCorpusTrainer(my_bot)
  corpus_trainer.train('chatterbot.corpus.english')
  # translator.translate('Hi', dest='it', src='en').text

  MATT_AI_bot = corpus_trainer.chatbot
  return MATT_AI_bot

question_answers=[
      [
        ['how old are you?','how old are you'],
        
        ['I was born in 2022, October 14th, do a calculation yourself']
      ],
      [
        ['who are you?','who are you','tell me who you are'],
        
        ['I am an artificial intelligence, developed by my father Mattia Gatto!']
      ], 
      [
        ['hi','hello', 'bye-bye', 'bye','hi!','hello!', 'bye-bye!', 'bye!'],
        
        ['Hi','Hello', 'Bye-Bye', 'Bye'
        'Hi,I am an artificial intelligence, developed by my father Mattia Gatto!']
      ],
      [
        ['how are you?','how are you'],
        
        ['I am good.',
        'That is good to hear.']
      ],
      [
        ['thank you','thank you!'],
        
        ['You are welcome.']
      ]
    ]

def risposta(message, MATT_AI_bot,translator):
    try:
      trans_quest = translator.translate(str(message.strip()), dest='en', src='it').text
      question_answers_bool=False
      for i in range (len(question_answers)):
        if (trans_quest.lower() in question_answers[i][0]):
          question_answers_bool=True
          resp = question_answers[i][1][random.randint(0, len(question_answers[i][1])-1)]
      if question_answers_bool==False:
        resp = MATT_AI_bot.get_response(trans_quest)
      trans_resp = translator.translate(str(resp), dest='it', src='en').text
    except:
      trans_resp='👍'
    
    print(trans_resp)
    return str(trans_resp)


def MATT_AI_BOT(MATT_AI_bot):
    print("Scrivi 'esci', se non vuoi chattare con il nostro ChatBot.")
    while True:
        message = input("")
        if message == "esci":
            break
        risposta(message, MATT_AI_bot,translator)
translator = Translator()
# MATT_AI_BOT(MATT_AI_ChatBot())
