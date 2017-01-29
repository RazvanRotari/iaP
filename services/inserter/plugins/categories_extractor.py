from nltk import word_tokenize
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer
import string

def tokenize(sentence):
    sentence = sentence.lower()
    tokens = word_tokenize(sentence)
    blacklisted_words = stopwords.words('english') + list(string.punctuation)
    return [word for word in tokens if word not in blacklisted_words]

def normalize_tokens(tokens):
   wnl = WordNetLemmatizer()
   return [wnl.lemmatize(word) for word in tokens]

def should_process(item):
    return len(item.categories) == 0

#Do the processing here. Needs to return an item
def process(item):
    title = item.title
    if not title:
        return item
    tokens = tokenize(title)
    item.categories = normalize_tokens(tokens)
    return item
