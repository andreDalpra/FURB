from unicodedata import name

import spotipy
from spotipy.oauth2 import SpotifyClientCredentials
import pandas as pd


class SpotifyAPI:
    def __init__(self, client_id, client_secret):
        self.client_id = client_id
        self.client_secret = client_secret
        self.auth_manager = SpotifyClientCredentials(client_id=self.client_id, client_secret=self.client_secret)
        self.sp = spotipy.Spotify(auth_manager=self.auth_manager)

    def get_artists_by_music(self, music: dict) -> str:
        """
            Gets the names of the artists for a given music track.
            ### Parameters
            - music (dict): A dictionary representing a music track, which should contain an 'artists' key with a list of artist dictionaries.
            ### Returns
            - str: A comma-separated string of artist names.
        """
        
        return ", ".join([artist['name'] for artist in music['artists']])

    def get_music_by_id(self, music_id: str) -> dict:
        """
            Gets the details of a music track by its Spotify ID.
            ### Parameters
            - music_id (str): The Spotify ID of the music track.
            ### Returns
            - dict: A dictionary containing the details of the music track.
        """
        return self.sp.track(music_id)

    def get_info(self, name: str) -> pd.DataFrame:
        """
            Searches for music tracks, albums, and episodes on Spotify by name.
            ### Parameters
            - name (str): The name of the music track, album, or episode to search for.
            ### Returns
            - pd.DataFrame: A DataFrame containing the search results with columns for 'Nome', 'Album', and 'Artista'.
        """ 
        resultado = []
        try:
            pesquisa = self.sp.search(q=name, type='album,track,episode')
            for i in range(50):
                musica = pesquisa['tracks']['items'][i]
                album = musica['album']
                resultado.append({'Nome': musica['name'],
                                'Album': album['name'],
                                'Artista': self.get_artists_by_music(musica)})
        except:
            return pd.DataFrame(resultado)
        
    def get_music(self, name:str, limit:int) -> dict:
        """
            Searches for music tracks on Spotify by name.
            ### Parameters
            - name (str): The name of the music track to search for.
            - limit (int): The maximum number of search results to return.
            ### Returns
            - dict: A dictionary containing the search results for music tracks.
        """
        return self.sp.search(q=name, type='track', limit=limit)
    
    def get_musicID(self, music:dict) -> str:
        """
            Gets the Spotify ID of a music track from the search results.
            ### Parameters
            - music (dict): A dictionary representing the search results for music tracks, which should contain a 'tracks' key with a list of track dictionaries.
            ### Returns
            - str: The Spotify ID of the first music track in the search results.
        """
        return music['tracks']['items'][0]['id']