<template>
  <div class="body">
      <div :class="{container:true}">
        <el-collapse-transition>
          <div v-show="show2">
        <div>
                <h3 style="  
                font-weight: 300;
                margin-top: 5%;
                margin-left: 45%;
                margin-bottom: 1.25rem;">
                请输入作业信息
                </h3>              
        </div>
        <el-table 
        :data="input_form" 
        style="width:620px; margin-left:9%"
        border  
        height="240"
        >
          <el-table-column label="序号" align="center">
              <template slot-scope="scope">
                  {{scope.$index + 1}}
              </template>
          </el-table-column>
          <el-table-column label="作业名" align="center" prop="jobName">
            <template slot-scope="scope">
              <el-input 
              v-model="scope.row.jobName"
              >
              </el-input>
            </template>
          </el-table-column>
          <el-table-column label="到达时间" align="center" prop="arriveTime">
            <template slot-scope="scope">
              <el-input 
              v-model="scope.row.arriveTime"></el-input>   
            </template>
          </el-table-column>
          <el-table-column label="服务时间" align="center" prop="serveTime">
            <template slot-scope="scope">
               <el-input 
               v-model="scope.row.serveTime"></el-input>   
            </template>
          </el-table-column>
 
          <el-table-column label="紧急程度" align="center" prop="prior">
            <template slot-scope="scope">
               <el-input 
               v-model="scope.row.prior"></el-input>   
            </template>
          </el-table-column>
          <el-table-column align="right">
            <template slot="header">
                 <el-button 
                @click="addGroup"
                type="primary"
                style="font-size:11px;width:70px;padding-left:10px;margin-left:-5px"
                >添加文件</el-button>
            </template>
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="danger"
                @click="delete(scope.$index)"
                style="margin-left:-5px"
                >
                删除
                </el-button>
            </template>
          </el-table-column>
          </el-table>               
        <button @click="nextstep()" class="btn" style="margin-top:20px">提 交</button>
      </div>
        </el-collapse-transition>
        <el-collopse-transition>
          <div v-show="show3">
          <el-table 
            :data="res_form"
            style="width:620px; margin-left:9%;marign-top:50px"
            border  
            height="240"
            >
              <el-table-column v-for="(item,id) in data2" :key="id">
                
              </el-table-column>
          </el-table>
          </div>
        </el-collopse-transition>
      </div>
  </div>
</template>

<script>
export default {
    data(){
        return{
            sum:"",
            input_form:[],
            res_form:[],
            data1:["序号","作业名","到达时间","服务时间","紧急程度"],
            data2:["进程名","到达时间","服务时间","开始时间","完成时间","周转时间"],
            show2:true,
            show3:false,
            num1:"" 
        }
    },
    created(){
      this.setTable();
    },
    methods:{
        delete(index){
          console.log(index);
        },
        setTable(){
            this.input_form = [
              {
                jobSeq:"0",
                jobName:"",
                arriveTime:"",
                serveTime:"",
                prior:""
            }
            ]
        },
        addGroup(){
          let num  = this.input_form.length;
          this.input_form.push({
                jobSeq:String(num),
                jobName:"",
                arriveTime:"",
                serveTime:"",
                prior:""
          })
        },
        nextstep(){
            let param = this.input_form;
            let that = this;
            this.axios.post("http://localhost:8080/api/FCFS",param).then((response) => {
              for(var i = 0;i<response.data.length;i++){
                that.res_form.push(response.data[i]);
              }
            })
            console.log(this.res_form);
            this.show2 = false
            this.show3 = true
        }
    }
}
</script>

<style>
    :root {
      /* COLORS */
      --white: #e9e9e9;
      --gray: #333;
      --blue: #0367a6;
      --lightblue: #008997;

      /* RADII */
      --button-radius: 0.7rem;

      /* SIZES */
      --max-width: 758px;
      --max-height: 420px;

      font-size: 16px;
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
        Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
    }
    * {
        margin: 0;
        padding: 0;
    }
    .body{
      align-items: center;
      background-color: var(--white);
      background: url("../assets/loginImage.jpg");
      background-attachment: fixed;
      background-position: center;
      background-repeat: no-repeat;
      background-size: cover;
      display: grid;
      height: 100vh;
      place-items: center;
    }
    .container{
      background-color: var(--white);
      border-radius: var(--button-radius);
      box-shadow: 0 0.9rem 1.7rem rgba(0, 0, 0, 0.25),
        0 0.7rem 0.7rem rgba(0, 0, 0, 0.22);
      height: var(--max-height);
      max-width: var(--max-width);
      overflow: hidden;
      position: relative;
      width: 100%; 
    }
     .form__title {
      font-weight: 300;
      margin-top: 10%;
      margin-left: 45%;
      margin-bottom: 1.25rem;
    }
    .el-table{
        border-radius:10px 10px 10px 10px
    }
     .btn {
      margin-top:60px;
      margin-left: 42%;
      background-color: var(--blue);
      background-image: linear-gradient(90deg, var(--blue) 0%, var(--lightblue) 74%);
      border-radius: 20px;
      border: 1px solid var(--blue);
      color: var(--white);
      cursor: pointer;
      font-size: 0.8rem;
      font-weight: bold;
      letter-spacing: 0.1rem;
      padding: 0.9rem 4rem;
      text-transform: uppercase;
      transition: transform 80ms ease-in;
    }
</style>