<template>
  <v-list-group sub-group class="text-left" value="true">
    <!-- 어떤 식으로든 이 위치에 태그가 없으면 Category 목록 FETCH 할때 제목, 메뉴버튼 컴포넌트가 사라진다.   -->
    <input type="hidden" />
    <template v-slot:activator>
      <v-list-item-title v-if="isEditMode">
        <v-text-field v-model="title" @click.stop="" @keydown.enter.stop="onEditCategory" @keydown.esc="cancelEdit" />
      </v-list-item-title>
      <v-list-item-title v-else class="font-weight-black text-h7">
        {{ category.title }}
      </v-list-item-title>
      <v-list-item-action v-if="category.id">
        <CategoryContextMenu :category="category" @edit-category="isEditMode = !isEditMode" />
      </v-list-item-action>
    </template>
    <v-list v-for="tag in category.tags" :key="tag.id">
      <CategorySubGroup :subItem="tag" />
    </v-list>
  </v-list-group>
</template>

<script>
import { EDIT_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions, mapMutations } from 'vuex';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import CategoryContextMenu from '@/views/header/component/category/CategoryContextMenu.vue';
import CategorySubGroup from '@/views/header/component/CategorySubGroup.vue';

export default {
  name: 'CategoryGroup',
  components: {
    CategorySubGroup,
    CategoryContextMenu,
  },
  props: {
    category: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      isEditMode: false,
      title: this.category.title,
    };
  },
  methods: {
    ...mapActions([EDIT_CATEGORY, FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onEditCategory() {
      try {
        await this[EDIT_CATEGORY]({ id: this.category.id, title: this.title });
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.EDIT.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.EDIT.FAIL);
      }
      this.cancelEdit();
    },
    cancelEdit() {
      this.isEditMode = false;
      this.title = this.category.title;
    },
  },
};
</script>
