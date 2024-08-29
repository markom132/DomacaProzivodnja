import globals from "globals";
import pluginJs from "@eslint/js";
import pluginReact from "eslint-plugin-react";

export default [
  {
    files: ["**/*.{js,mjs,cjs,jsx}"],
    languageOptions: {
      globals: globals.browser,
    },
    rules: {
      "no-console": "warn",
      "no-unused-vars": "warn",
      "quotes": ["error", "single"],
      "semi": ["error", "always"],
      "react/jsx-filename-extension": [1, { extensions: [".js", ".jsx"] }],
      "react/prop-types": "off",
    },
  },
  pluginJs.configs.recommended,
  pluginReact.configs.flat.recommended,
];
