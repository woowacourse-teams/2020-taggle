<template>
  <v-layout>
    <v-menu bottom :offset-x="true">
      <template v-slot:activator="{ on, attrs }">
        <v-btn icon v-bind="attrs" v-on="on" @click.prevent="">
          <v-icon>mdi-dots-horizontal</v-icon>
        </v-btn>
      </template>

      <v-list>
        <slot name="menuItems" />
      </v-list>
    </v-menu>
    <slot name="menuDialog" />
  </v-layout>
</template>

<script>
export default {
  name: 'ContextMenu',
};
</script>
