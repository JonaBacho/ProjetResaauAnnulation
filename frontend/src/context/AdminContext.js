"use client";
import { createContext, useState, useEffect } from 'react';
import axios from 'axios';
import Swallow from "sweetalert2";

export const AdminContext = createContext(null);

export const AdminProvider = ({ children }) => {


    const [user, setUser] = useState(null);
    useEffect(() => {
        if (typeof window !== 'undefined') {
            const token = localStorage.getItem('token');
            if (token) {
                setUser({ token });
            }
        }
    }, []);


    const login = async (loginData) => {
        try {
            const response = await axios.post('http://localhost:8000/api/login', loginData);
            const data = response.data;

            localStorage.setItem('token', data.token);
            setUser({ token: data.token });
            await Swallow.fire({
                icon: 'success',
                title: 'Authentification réussie',
                text: 'Vous vous etes authententifé avec succès!',
            });
            if (typeof window !== 'undefined') {
                window.location.href = '/AnnulationAgencePage';
            }
        } catch (error) {
            await Swallow.fire({
                icon: 'error',
                title: 'Authentification echouée',
                text: 'Mots de passe ou email incorrects. Reessayez.',
            });
            console.error('Login failed', error);
        }
    };


    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        if (typeof window !== 'undefined') {
            window.location.href = '/';
        }
    };

    return (
        <AdminContext.Provider value={{ user, login, logout }}>
            {children}
        </AdminContext.Provider>
    );
};


