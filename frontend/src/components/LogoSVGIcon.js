import React from "react";

const LogoSVGIcon = () => (
    <svg
        viewBox="-1.5 0 250 250"
        xmlns="http://www.w3.org/2000/svg"
        xmlnsXlink="http://www.w3.org/1999/xlink"
        preserveAspectRatio="xMidYMid"
        fill="#000000"
    >
        <defs>
            <filter id="shadow" x="-20%" y="-20%" width="140%" height="140%">
                <feDropShadow dx="5" dy="5" stdDeviation="5" floodColor="rgba(0, 0, 0, 0.3)" />
            </filter>
            <radialGradient id="light-gradient" cx="50%" cy="50%" r="50%" fx="50%" fy="50%">
                <stop offset="0%" stopColor="rgba(255, 255, 255, 0.4)" stopOpacity="0.6" />
                <stop offset="100%" stopColor="rgba(255, 255, 255, 0)" />
            </radialGradient>
            <linearGradient id="apple-gradient" x1="0" y1="0" x2="0" y2="1">
                <stop offset="0%" stopColor="#FFE066"></stop>
                <stop offset="100%" stopColor="#FFA000"></stop>
            </linearGradient>
        </defs>
        <g id="apple" filter="url(#shadow)">
            <path
                d="M111.020,70.812 C110.505,63.506 109.359,55.069 107.000,46.812 C102.740,31.902 91.438,14.459 89.438,11.259 C87.438,8.059 86.800,3.412 92.000,0.812 C97.200,-1.788 101.800,2.394 105.000,6.812 C105.000,6.812 116.400,23.213 122.000,42.812 C124.659,52.119 125.965,62.213 126.588,70.812 C126.588,70.812 111.020,70.812 111.020,70.812 Z"
                fill="#987160"
                fillRule="evenodd"
            ></path>
            <path
                d="M183.065,5.528 C217.473,-3.692 244.464,4.784 245.972,10.413 C247.653,16.685 228.344,36.877 193.935,46.097 C159.527,55.317 133.743,51.348 131.027,41.212 C128.311,31.076 148.656,14.748 183.065,5.528 Z"
                fill="#006400"
                fillRule="evenodd"
            ></path>
            <path
                d="M245.500,10.500 C220.000,20.000 160.000,30.000 130.000,42.500"
                stroke="#2A623D"
                strokeWidth="2"
                fill="none"
            ></path>
            <path
                d="M148.000,249.812 C137.636,249.812 127.701,247.839 118.500,244.225 C109.299,247.839 99.364,249.812 89.000,249.812 C41.503,249.812 -0.000,187.399 -0.000,136.312 C-0.000,85.226 21.503,59.812 69.000,59.812 C85.139,59.812 100.167,69.219 118.500,69.219 C136.833,69.219 151.861,59.812 168.000,59.812 C215.496,59.812 237.000,85.226 237.000,136.312 C237.000,187.399 195.496,249.812 148.000,249.812 Z"
                fill="url(#apple-gradient)"
                fillRule="evenodd"
            ></path>
            <circle cx="118.5" cy="120" r="80" fill="url(#light-gradient)" />
        </g>
    </svg>
);

export default LogoSVGIcon;
