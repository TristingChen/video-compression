<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
          <el-form-item label="绝对文件目录" prop="fullFileDirectory">
        <el-input v-model="dataForm.fullFileDirectory" placeholder="绝对文件目录"></el-input>
      </el-form-item>
          <el-form-item label="绝对路径" prop="fullFilePath">
        <el-input v-model="dataForm.fullFilePath" placeholder="绝对路径"></el-input>
      </el-form-item>
          <el-form-item label="文件名称" prop="fileName">
        <el-input v-model="dataForm.fileName" placeholder="文件名称"></el-input>
      </el-form-item>
          <el-form-item label="压缩状态" prop="status">
        <el-input v-model="dataForm.status" placeholder="压缩状态"></el-input>
      </el-form-item>
          <el-form-item label="异常的消息" prop="msg">
        <el-input v-model="dataForm.msg" placeholder="异常的消息"></el-input>
      </el-form-item>
          <el-form-item label="视频长度" prop="duration">
        <el-input v-model="dataForm.duration" placeholder="视频长度"></el-input>
      </el-form-item>
          <el-form-item label="" prop="streamNum">
        <el-input v-model="dataForm.streamNum" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="文件大小" prop="fileSize">
        <el-input v-model="dataForm.fileSize" placeholder="文件大小"></el-input>
      </el-form-item>
          <el-form-item label="压缩后文件大小" prop="compressFileSize">
        <el-input v-model="dataForm.compressFileSize" placeholder="压缩后文件大小"></el-input>
      </el-form-item>
          <el-form-item label="" prop="createdAt">
        <el-input v-model="dataForm.createdAt" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="updatedAt">
        <el-input v-model="dataForm.updatedAt" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="" prop="fileConfigId">
        <el-input v-model="dataForm.fileConfigId" placeholder=""></el-input>
      </el-form-item>
          <el-form-item label="转码花费的时间" prop="compressDuration">
        <el-input v-model="dataForm.compressDuration" placeholder="转码花费的时间"></el-input>
      </el-form-item>
      </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();

const dataForm = reactive({
  id: '',  fullFileDirectory: '',  fullFilePath: '',  fileName: '',  status: '',  msg: '',  duration: '',  streamNum: '',  fileSize: '',  compressFileSize: '',  createdAt: '',  updatedAt: '',  fileConfigId: '',  compressDuration: ''});

const rules = ref({
          fullFileDirectory: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          fullFilePath: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          fileName: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          status: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          msg: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          duration: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          streamNum: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          fileSize: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          compressFileSize: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          createdAt: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          updatedAt: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          fileConfigId: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          compressDuration: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ]
  });

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  if (id) {
    getInfo(id);
  }
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/file/videoinfo/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/file/videoinfo", dataForm).then((res) => {
      ElMessage.success({
        message: '成功',
        duration: 500,
        onClose: () => {
          visible.value = false;
          emit("refreshDataList");
        }
      });
    });
  });
};

defineExpose({
  init
});
</script>
