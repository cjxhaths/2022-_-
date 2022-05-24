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
               v-model="scope.row.serveTime"
               >
               </el-input>   
            </template>
          </el-table-column>
 
          <el-table-column label="紧急程度" align="center" prop="prior">
            <template slot-scope="scope">
               <el-input 
               v-model="scope.row.prior"
               >
               </el-input>   
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
        <button @click="visible = true" class="btn" style="margin-top:20px">提 交</button>
        <el-dialog
          title="请选择调度算法类型"
          :visible.sync="visible"
          width="30%"
        >
            <span>
              <el-radio-group
                v-model="num"
                style="margin-left:12%"
                :before-close="handleClose"
              >
              <el-radio :label="1">FCFS算法</el-radio>
              <el-radio :label="2">HRRN算法</el-radio>
              <el-radio :label="3">SJF算法</el-radio>
              </el-radio-group>
            </span>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="nextstep">确 定</el-button>
              <el-button @click="visible = false">取 消</el-button>
            </span>
          </el-dialog>
      </div>
        </el-collapse-transition>
        <el-collopse-transition>
          <div v-show="show3" style="margin-top:6%">
          <h3 
            style="margin-left:43%;margin-top:5%;margin-bottom:30px;font-weight: 300;"
          >{{this.algorithm}}</h3>
          <el-table 
            :data="res_form"
            style="width:620px; margin-left:9%;marign-top:10%"
            border  
            height="240"
            >
              <el-table-column v-for="(item,id) in data2" :key="id" :label="item" align="center">
                <template slot-scope="scope">
                  <div v-if="id===0" align="center">{{scope.row.jobName}}</div>
                  <div v-else-if="id===1" align="center">{{scope.row.arriveTime}}</div>
                  <div v-else-if="id===2" align="center">{{scope.row.serveTime}}</div>
                  <div v-else-if="id===3" align="center">{{scope.row.startTime}}</div>
                  <div v-else-if="id===4" align="center">{{scope.row.completeTime}}</div>
                  <div v-else align="center">{{scope.row.cyclingTime}}</div>
                </template>
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
            input_form:[],
            res_form:[],
            data2:["进程名","到达时间","服务时间","开始时间","完成时间","周转时间"],
            show2:true,
            show3:false,
            visible:false,
            num:1,
            algorithm:"",
        }
    },
    created(){
      this.setTable();
    },
    methods:{
        handleClose(done){
          done();
        },
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
            console.log(this.num);
            console.log(this.algorithm);
            if(Number(this.num) === 1){
                this.axios.post("http://localhost:8080/api/FCFS",param).then((response) => {
                  this.algorithm = "FCFS调度算法"
                for(var i = 0;i<response.data.length;i++){
                  that.res_form.push(response.data[i]);
                }
              })
            }
            else if(Number(this.num) === 2){
                this.algorithm = "HRRN调度算法"
                this.axios.post("http://localhost:8080/api/HRRN",param).then((response) => {
                for(var i = 0;i<response.data.length;i++){
                  that.res_form.push(response.data[i]);
                }
              })
            }
            else{
                this.axios.post("http://localhost:8080/api/SJF",param).then((response) => {
                this.algorithm = "SJF调度算法"
                for(var i = 0;i<response.data.length;i++){
                  that.res_form.push(response.data[i]);
                }
              })
            }
            this.show2 = false
            this.show3 = true
            this.visible = false
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