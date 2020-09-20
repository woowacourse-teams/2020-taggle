<template>
  <v-dialog v-model="dialog" width="500">
    <v-card>
      <v-app-bar dense flat>
        <v-card-title>카테고리 변경</v-card-title>
        <v-spacer></v-spacer>
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-app-bar>

      <v-card-text class="pa-4">
        <v-form ref="categoryEditForm" v-model="valid">
          <v-select
            v-model="categoryId"
            :items="allCategoriesForSelect"
            dense
            :rules="rules.category.changeTitleFromTag"
            outlined
            label="변경하려는 카테고리를 선택해주세요."
          />
        </v-form>
      </v-card-text>
      <v-card-actions class="pt-0 px-4 pb-4">
        <v-spacer />
        <v-btn depressed large :disabled="!valid" @click.prevent="onChangeCategory">수정</v-btn>
        <v-btn depressed large @click="dialog = false">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { UPDATE_TAG, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { ALL_CATEGORIES_FOR_SELECT } from '@/store/share/getterTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { mapActions, mapGetters, mapMutations } from 'vuex';
import validator from '@/utils/validator.js';

export default {
  name: 'CategoryEditModal',
  props: {
    tag: {
      type: Object,
      required: true,
    },
    editDialog: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      rules: { ...validator },
      valid: false,
      dialog: false,
      categoryId: '',
    };
  },
  watch: {
    editDialog(value) {
      if (value) {
        this.dialog = value;
      }
    },
    dialog() {
      if (!this.dialog) {
        this.categoryId = '';
        this.$refs.categoryEditForm.resetValidation();
        this.$emit('close');
      }
    },
  },
  computed: {
    ...mapGetters([ALL_CATEGORIES_FOR_SELECT]),
  },
  methods: {
    ...mapActions([UPDATE_TAG, FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onChangeCategory() {
      if (!this.$refs.categoryEditForm.validate()) {
        return;
      }
      try {
        await this[UPDATE_TAG]({
          updateTagRequest: { categoryId: this.categoryId },
          tagId: this.tag.id,
        });
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.TAG.EDIT.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG.EDIT.FAIL);
      } finally {
        this.dialog = false;
      }
    },
  },
};
</script>
