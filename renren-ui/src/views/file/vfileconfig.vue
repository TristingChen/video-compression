<template>
  <div class="mod-file__vfileconfig">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('file:vfileconfig:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>

    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
              <el-table-column prop="id" label="id" header-align="center" align="center"></el-table-column>
              <el-table-column prop="globalFilePath" label="文件路径" header-align="center" align="center"></el-table-column>
              <el-table-column prop="desc" label="描述" header-align="center" align="center"></el-table-column>
              <el-table-column prop="createdAt" label="创建时间" header-align="center" align="center"></el-table-column>
              <el-table-column prop="updatedAt" label="修改时间" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('file:vfileconfig:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定</add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs } from "vue";
import AddOrUpdate from "./vfileconfig-add-or-update.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/sys/file-config/list",
  getDataListIsPage: false,
  exportURL: "/file/vfileconfig/export",
  deleteURL: "/file/vfileconfig"
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
