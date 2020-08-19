<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on" class="mx-2" small icon>
        <v-icon dark>mdi-cog</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="headline grey lighten-2">
        카테고리 수정
      </v-card-title>

      <v-card-text class="pt-6">
        <v-card class="mx-auto" max-width="100%" tile>
          <v-list v-for="{ id, title, tags } in categories" :key="id" class="grow">
            <v-list-group class="text-left mt-2" link value="true">
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title class="font-weight-black text-h7">{{ title }}</v-list-item-title>
                </v-list-item-content>
              </template>
              <draggable
                class="list-group"
                :list="tags"
                group="categoryTag"
                handle=".handle"
                @change="changeTag($event, id)"
              >
                <v-list-item v-for="{ id, name } in tags" :key="id">
                  <v-list-item-icon>
                    <v-icon class="handle">mdi-format-align-justify</v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                    <span class="text">{{ name }} </span>
                  </v-list-item-content>
                </v-list-item>
              </draggable>
            </v-list-group>
          </v-list>
        </v-card>
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
import draggable from 'vuedraggable';
import { CATEGORIES } from '@/store/share/getterTypes.js';
import { mapGetters } from 'vuex';
import CategoryService from '@/api/module/category.js';

export default {
  name: 'CategoryTagModifyModal',
  components: {
    draggable,
  },
  data() {
    return {
      dialog: false,
    };
  },
  computed: {
    ...mapGetters([CATEGORIES]),
  },
  methods: {
    async changeTag({ added }, id) {
      await CategoryService.changeTag(id, added.element.id);
    },
  },
};
</script>
