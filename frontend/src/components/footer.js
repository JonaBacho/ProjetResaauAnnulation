import React from 'react';
//import Logo from '../assets/pictures/logo1.jpg'
import { FaQuestion, FaEnvelope, FaShieldAlt } from 'react-icons/fa';




const Footer = () => {
    return (
        <footer>
            <div className='w-full h-38 bg-white'>
                <div className='bg-tertiary w-full flex justify-between shadow-lg' >
                    <div className='ml-20'>
                        <a  href ="#" className= 'py-3 text-2xl text-primary flex gap-4 mb-5 font-bold cursor-pointer'>
                            {/* <img className=' w-20 mr-2' src=" " alt ="logo" /> */}
                            <p className='mt-6'>Mooving.com</p>
                        </a>

                    </div>
                    <div className='mt-12 mr-20 '>
                        <div className='flex item-center gap-4 text-black cursor-pointer text-bold text-xl '>
                            <a href="#"  class="flex items-center hover:rounded-3xl hover:p-2 hover:bg-primary hover:text-white transition-all duration-300">
                                <FaQuestion className='mr-1'/>
                                Aide
                            </a>
                            <a href="#" class="flex items-center  hover:rounded-3xl hover:p-2 hover:bg-primary hover:text-white transition-all duration-300">
                                <FaEnvelope className='mr-1'/>
                                Contact
                            </a>
                            <a href="#" class="flex items-center   hover:rounded-3xl hover:p-2 hover:bg-primary hover:text-white transition-all duration-300">
                                <FaShieldAlt className='mr-1'/>
                                Sécurité
                            </a>
                        </div>
                    </div>
                </div>
                <div className='flex flex-col ml-20'>
                    <div className="flex justify-left mt-3 mb-5 gap-4" >
                        <p className='text-gray-600'>&copy; 2023-2024 Mooving.com Tous droits réservés.</p>
                        <div className='flex justify-center gap-3 text-bold '>
                            <a className='focus:rounded-3xl focus:p-2 hover:rounded-3xl hover:p-2 hover:bg-primary hover:text-tertiary transition-all duration-300'>Respect de la vie privée</a>
                            <a className='focus:rounded-3xl focus:p-2  hover:rounded-3xl hover:p-2 hover:bg-primary hover:text-tertiary transition-all duration-300'>Coockie</a>
                            <a className='focus:rounded-3xl focus:p-2  hover:rounded-3xl hover:p-2 hover:bg-primary hover:text-tertiary transition-all duration-300'>Contrats d'utilisation</a>
                        </div>

                    </div>
                </div>
                <div className='mt-5 mb-5 mr-20 ml-20'>
                    <p className='text-gray-500'>
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Expedita consequatur
                        non rem sunt porro, dignissimos autem iste impedit? Suscipit, distinctio.
                        Voluptatibus sed sequi temporibus non autem debitis, sit ipsam minima?
                    </p>

                </div>
            </div>
        </footer>
    );
};

export default Footer;



