<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on" class="mx-2" fab dark small color="indigo">
        <v-icon dark>mdi-plus</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="headline grey lighten-2">
        카테고리 추가
      </v-card-title>

      <v-card-text class="pt-6">
        <v-text-field
          @keypress.enter.prevent="addCategory"
          v-model="categoryName"
          :error-messages="errorMessages"
          dense
          label="Category"
        ></v-text-field>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="dialog = false">
          닫기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { CREATE_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions } from 'vuex';

export default {
  name: 'CategoryAddModal',
  data() {
    return {
      categoryName: '',
      dialog: false,
      errorMessages: '',
    };
  },
  methods: {
    ...mapActions([CREATE_CATEGORY, FETCH_CATEGORIES]),
    async addCategory() {
      try {
        await this[CREATE_CATEGORY]({ title: this.categoryName });
        await this[FETCH_CATEGORIES]();
      } catch (e) {
        this.errorMessages = e;
      }
      this.categoryName = '';
      this.errorMessages = '';
    },
  },
};
</script>
