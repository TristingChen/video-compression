<template>
  <div class="mod-file__videoinfo">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-input v-model="state.dataForm.fileName" placeholder="文件名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%">



              <el-table-column type="index" label="序号"   width="50"> </el-table-column>
              <el-table-column prop="fileName" label="文件名称" header-align="center" align="center"></el-table-column>
              <el-table-column prop="fullFileDirectory" label="绝对文件目录" header-align="center" align="center"></el-table-column>
              <el-table-column prop="fullFilePath" label="绝对路径" header-align="center" align="center"></el-table-column>
              <el-table-column prop="statusName" label="压缩状态" header-align="center" align="center"></el-table-column>
              <el-table-column prop="duration" label="视频长度(秒)" header-align="center" align="center"></el-table-column>
              <el-table-column prop="fileSize" label="文件大小(MB)" header-align="center" align="center"></el-table-column>
              <el-table-column prop="compressFileSize" label="压缩后文件大小(MB)" header-align="center" align="center"></el-table-column>
              <el-table-column prop="compressDuration" label="转码花费的时间(秒)" header-align="center" align="center"></el-table-column>
              <el-table-column prop="msg" label="异常的消息" header-align="center" align="center"></el-table-column>
              <el-table-column prop="createdAt" label="创建时间" header-align="center" align="center"></el-table-column>


    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定</add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs } from "vue";
import AddOrUpdate from "./videoinfo-add-or-update.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/sys/video-info/page",
  getDataListIsPage: true,
  exportURL: "/file/videoinfo/export",
  deleteURL: "/file/videoinfo",
  dataForm: {
    fileName: ""
  }
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
