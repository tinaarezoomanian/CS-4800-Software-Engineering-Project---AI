"""
FastAPI backend for the frontend. Defines the `app` instance and routes such as
/summarize and serving index.html. Run with:
    uvicorn application.app:app --reload
"""

from fastapi import FastAPI
from fastapi.responses import FileResponse
from pydantic import BaseModel
from pathlib import Path
from application.summarize import summarize_text
from fastapi.staticfiles import StaticFiles


app = FastAPI()
app.mount("/static", StaticFiles(directory="static"), name="static")

class In(BaseModel):
    text: str


@app.post("/summarize")
def summarize(i: In):
    return {"summary": summarize_text(i.text)}


# Serve the project's index.html at the root path.
# Resolves the file relative to the repository root (one level above this module).
INDEX_PATH = Path(__file__).resolve().parent.parent / "./index.html"


@app.get("/", response_class=FileResponse)
def serve_index():
    """Return the project's index.html file if present, otherwise 404."""
    if INDEX_PATH.is_file():
        return FileResponse(INDEX_PATH)
    # If index.html isn't found, return a simple 404 message (FastAPI will handle status code).
    return FileResponse(str(INDEX_PATH), status_code=404)