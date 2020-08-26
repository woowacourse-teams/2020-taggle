<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn fab v-bind="attrs" v-on="on" class="mx-2" large icon>
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-app-bar dense flat>
        <v-card-title>카테고리 추가</v-card-title>
        <v-spacer />
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-app-bar>

      <v-card-text class="pa-4">
        <v-form ref="createCategoryForm" @submit.prevent="onAddCategory">
          <v-text-field
            v-model="createCategoryRequest.title"
            dense
            :rules="rules"
            label="이름을 입력후에 enter를 입력하여 저장."
          />
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapActions, mapMutations } from 'vuex';
import { CREATE_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

export default {
  name: 'CategoryAddModal',
  data() {
    return {
      createCategoryRequest: {
        title: '',
      },
      rules: validator.category.title,
      dialog: false,
    };
  },
  watch: {
    dialog() {
      if (!this.dialog) {
        this.createCategoryRequest.title = '';
        this.$refs.createCategoryForm.resetValidation();
      }
    },
  },
  methods: {
    ...mapActions([CREATE_CATEGORY, FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onAddCategory() {
      if (!this.$refs.createCategoryForm.validate()) {
        return;
      }
      try {
        await this[CREATE_CATEGORY](this.createCategoryRequest);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.ADD.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.ADD.FAIL);
      } finally {
        this.dialog = false;
      }
    },
  },
};
</script>
