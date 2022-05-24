module.exports = {
  devServer:{
    port: 8080,
    open:false,
    proxy:{
      '/api':{
        target:'http://localhost:8999/',
        changeOrigin:true,
        pathRewrite:{
          '^/api':''
        }
      }
    }
  },
  lintOnSave: false
}