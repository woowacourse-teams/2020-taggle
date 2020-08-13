module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: ['plugin:vue/essential', 'airbnb-base', '@vue/prettier'],
  parserOptions: {
    parser: 'babel-eslint',
  },
  rules: {
    semi: ['error', 'always'],
    'comma-dangle': 'off',
    'no-new': 'off',
    indent: ['error', 2],
    'import/no-unresolved': 'off',
    'import/extensions': ['error', 'always'],
    'no-param-reassign': 0,
    'no-console': 'off',
  },
};
