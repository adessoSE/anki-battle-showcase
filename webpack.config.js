var path = require('path');
var HardSourceWebpackPlugin = require('hard-source-webpack-plugin');

module.exports = {
  entry: './src/main/reactjs/index.jsx',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'src/main/resources/public/assets')
  },
  
  devtool: "source-map",
  
  module: {
    rules: [
      { test: /\.jsx?$/, loader: 'babel-loader', options: { presets: ['env', 'react'] } },
      { test: /\.css$/, use: ['style-loader', 'css-loader'] },
      { test: /\.scss$/, use: ['style-loader', 'css-loader', 'sass-loader'] }
    ]
  },
  
  resolve: {
    alias: {
      Components: path.resolve(__dirname, 'src/main/reactjs/components'),
      Services: path.resolve(__dirname, 'src/main/reactjs/services'),
      Views: path.resolve(__dirname, 'src/main/reactjs/views')
    },
    extensions: ['.js', '.jsx']
  },

    plugins: [
        new HardSourceWebpackPlugin()
    ]
};
