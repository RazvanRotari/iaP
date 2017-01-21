FROM python:3.5
RUN mkdir /code
COPY requirements.txt /code/
RUN pip3 install -r /code/requirements.txt
COPY inserter.py model.py  /code/
CMD ["python", "/code/inserter.py"]
