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
    <v-list-item v-for="{ id, name } in category.tags" :key="id" :to="{ name: 'bookmarks', params: { id } }">
      <!--            <v-list-item-title @click.prevent="">-->
      <!--              <v-text-field :value="name" />-->
      <!--            </v-list-item-title>-->
      <v-list-item-title>
        {{ name }}
      </v-list-item-title>
      <v-list-item-action>
        <TagContextMenu :tag="{ id }" />
      </v-list-item-action>
    </v-list-item>
  </v-list-group>
</template>

<script>
import CategoryContextMenu from '@/views/header/component/category/CategoryContextMenu.vue';
import TagContextMenu from '@/views/header/component/category/TagContextMenu.vue';
import { EDIT_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions, mapMutations } from 'vuex';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';

export default {
  name: 'CategoryGroup',
  components: {
    CategoryContextMenu,
    TagContextMenu,
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
