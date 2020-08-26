<template>
  <ContextMenu>
    <template slot="menuItems">
      <v-list-item v-for="(item, index) in items" :key="index" @click="item.action">
        <v-list-item-title>{{ item.title }}</v-list-item-title>
        <v-list-item-action>
          <v-icon>
            {{ item.icon }}
          </v-icon>
        </v-list-item-action>
      </v-list-item>
    </template>
    <template slot="menuDialog">
      <CategoryEditModal :tag="tag" :editDialog="editCategoryDialog" @close="editCategoryDialog = false" />
      <ConfirmDialog ref="confirm" />
    </template>
  </ContextMenu>
</template>

<script>
import TagService from '@/api/module/tag.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { mapActions, mapMutations } from 'vuex';
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import ContextMenu from '@/views/header/component/category/ContextMenu.vue';
import CategoryEditModal from '@/views/header/component/category/CategoryEditModal.vue';
import ConfirmDialog from '@/views/common/component/ConfirmDialog.vue';

export default {
  name: 'TagContextMenu',
  components: {
    ConfirmDialog,
    ContextMenu,
    CategoryEditModal,
  },
  props: {
    tag: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      items: [
        { title: '카테고리 변경', icon: 'mdi-pencil', action: this.openEditDialog },
        { title: '태그 삭제', icon: 'mdi-delete', action: this.onDeleteTag },
      ],
      editCategoryDialog: false,
    };
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onDeleteTag() {
      const confirm = await this.$refs.confirm.open('태그 삭제', '정말로 해당 태그를 삭제하시겠습니까?');
      if (!confirm) {
        return;
      }
      try {
        await TagService.delete(this.tag.id);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.TAG.DELETE.SUCCESS);
      } catch {
        this[SHOW_SNACKBAR](MESSAGES.TAG.DELETE.FAIL);
      }
    },
    openEditDialog() {
      this.editCategoryDialog = true;
    },
  },
};
</script>
